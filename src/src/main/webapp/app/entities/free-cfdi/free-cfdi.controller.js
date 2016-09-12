(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiController', Free_cfdiController);

    Free_cfdiController.$inject = ['$scope', 'DataUtils','$filter','$state', 'Free_cfdi', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'Cfdi_states'];

    function Free_cfdiController ($scope, DataUtils, $filter, $state, Free_cfdi, ParseLinks, AlertService, pagingParams, paginationConstants, Cfdi_states) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.search = search;
        vm.cancelar = cancelar;
        vm.abrirZip = abrirZip;
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
        vm.cfdi_all = null;

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
                vm.cfdi_all = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function search() {
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
            if(vm.folio_fiscal != null && vm.folio_fiscal != ""){
                foliof = vm.folio_fiscal;
            }
            var folio = " ";
            if(vm.folio != null && vm.folio != ""){
                folio = vm.folio;
            }
            var idFree_cfdi = 0;
            if(vm.free_cfdi != null){
                idFree_cfdi = vm.free_cfdi.id;
            }
            var serie = " ";
            if(vm.serie != null && vm.serie != ""){
                serie = vm.serie;
            }
            var rfcreceiver = " ";
            if(vm.rfc_receiver != null && vm.rfc_receiver != ""){
                rfcreceiver = vm.rfc_receiver;
            }
            var idstate = 0;
            if(vm.state != null){
                idstate = vm.state.id;
            }
            Free_cfdi.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                idFree_cfdi: idFree_cfdi,
                folio_fiscal: foliof,
                rfc_receiver: rfcreceiver,
                fromDate: fromDate,
                toDate: toDate,
                idState: idstate,
                serie: serie,
                folio: folio
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

        function cancelar(free_cfdi){
            free_cfdi.cfdi_states = vm.states[1];
            var free_cfdi_dto = {
                freeCFDI: free_cfdi,
                concepts: null,
                freeTaxTransfereds: null,
                freeTaxRetentions: null
            };
            Free_cfdi.update(free_cfdi_dto);
            loadAll();
        }

        function abrirZip(free_cfdi){
            var temp = Free_cfdi.getzip({idFree_cfdi:free_cfdi.id});
            var zipfile = temp.filexml;
            vm.openFile('',zipfile);
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

        vm.openFile = DataUtils.openFile;
    }
})();
