(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_countryController', C_countryController);

    C_countryController.$inject = ['$scope', '$state', 'C_country', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function C_countryController ($scope, $state, C_country, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.filtername = null;
        vm.onChangeName = onChangeName;
        vm.loadAll();

        function loadAll () {
            C_country.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                pg: 0,
                filtername:" "
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
                vm.c_countries = data;
                vm.c_countryS = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
            C_country.query({
                pg: 1,
                filtername:" "
            }, onSuccess1, onError1);
            function onSuccess1(data, headers) {
                vm.c_countryS = data;
            }
            function onError1(error) {
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
            if (vm.selectedc_country != null) {
                vm.filtername = vm.selectedc_country.name;
            } else {
                vm.filtername = " ";
            }
            C_country.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                pg: 0,
                filtername:vm.filtername
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
                vm.c_countries = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
