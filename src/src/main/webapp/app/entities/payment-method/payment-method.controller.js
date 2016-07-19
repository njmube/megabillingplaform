(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Payment_methodController', Payment_methodController);

    Payment_methodController.$inject = ['$scope', '$state', 'Payment_method', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function Payment_methodController ($scope, $state, Payment_method, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.filtername = null;
        vm.filtercode = null;
        vm.onChangeName = onChangeName;
        vm.loadAll();

        function loadAll () {
            Payment_method.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                filtername:" ",
                filtercode:" "
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.payment_methods = data;
                vm.payment_methodS = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function onChangeName() {
            if (vm.selectedpayment_method != null) {
                vm.filtername = vm.selectedpayment_method.name;
            } else {
                vm.filtername = " ";
            }
            if(vm.filtercode == null || vm.filtercode == ""){
                vm.filtercode = " ";
            }
            Payment_method.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                filtername:vm.filtername,
                filtercode:vm.filtercode
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.payment_methods = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

    }
})();
