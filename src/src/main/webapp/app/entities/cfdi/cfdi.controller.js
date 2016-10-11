(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiController', CfdiController);

    CfdiController.$inject = ['$scope', '$filter', '$state', 'Cfdi', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function CfdiController ($scope, $filter,  $state, Cfdi, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        loadAll();

        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';

            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            if(vm.dateStart != null){
                fromDate = $filter('date')(vm.dateStart, dateFormat);
            }
            if(vm.dateEnd != null){
                toDate = $filter('date')(vm.dateEnd, dateFormat);
            }
            var foliof = " ";

            var folio = " ";

            var serie = " ";

            var rfcreceiver = " ";

            var idcfdi_type_doc = 0;

            Cfdi.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                folio_fiscal: foliof,
                rfc_receiver: rfcreceiver,
                fromDate: fromDate,
                toDate: toDate,
                idcfdi_type_doc: idcfdi_type_doc,
                serie: serie,
                folio: folio,
                idaccount: 0,
                pre: 0,
                send: 0,
                cancel: 1,
                reciever: 0
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
                vm.cfdis = data;
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
