(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Branch_officeController', Branch_officeController);

    Branch_officeController.$inject = ['$scope', '$state', 'Branch_office', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function Branch_officeController ($scope, $state, Branch_office, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        loadAll();

        function loadAll () {
            Branch_office.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                taxpayeraccount: 0
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
                vm.branch_offices = data;
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
    }
})();
