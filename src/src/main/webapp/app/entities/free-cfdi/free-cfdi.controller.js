(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiController', Free_cfdiController);

    Free_cfdiController.$inject = ['$scope', '$filter','$state', 'Free_cfdi', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'Cfdi_states'];

    function Free_cfdiController ($scope,$filter, $state, Free_cfdi, ParseLinks, AlertService, pagingParams, paginationConstants, Cfdi_states) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.search = search;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAll();
        vm.folio_fiscal = null;
        vm.rfc_receiver = null;
        vm.free_cfdi = null;
        vm.dateStart = null;
        vm.dateEnd = null;
        vm.folio = null;
        vm.serie = null;
        vm.state = null;
        vm.states = Cfdi_states.query({filtername:" "});

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            Free_cfdi.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                idFree_cfdi: 0,
                folio_fiscal: " ",
                rfc_receiver: " ",
                fromDate: fromDate,
                toDate: toDate,
                idState: 0,
                serie: " ",
                folio: " "
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
                vm.free_cfdis = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function search () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            var idFree_cfdi = vm.free_cfdi.id;
            Free_cfdi.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                idFree_cfdi: idFree_cfdi,
                folio_fiscal: " ",
                rfc_receiver: " ",
                fromDate: fromDate,
                toDate: toDate,
                idState: 0,
                serie: " ",
                folio: " "
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
                vm.free_cfdis = data;
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

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };

        vm.datePickerOpenStatus1 = {};
        vm.datePickerOpenStatus1.date = false;

        vm.openCalendar1 = function(date) {
            vm.datePickerOpenStatus1[date] = true;
        };

    }
})();
