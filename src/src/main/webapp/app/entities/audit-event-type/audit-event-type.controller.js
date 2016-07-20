(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Audit_event_typeController', Audit_event_typeController);

    Audit_event_typeController.$inject = ['$scope', '$state', 'Audit_event_type', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function Audit_event_typeController ($scope, $state, Audit_event_type, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAll();

        function loadAll () {
            Audit_event_type.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                pg:0
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
                vm.audit_event_types = data;
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
