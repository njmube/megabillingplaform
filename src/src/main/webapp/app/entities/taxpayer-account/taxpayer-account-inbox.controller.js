(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountInboxController', Taxpayer_accountInboxController);

    Taxpayer_accountInboxController.$inject = ['$scope', 'Cfdi_type_doc', 'entity','Taxpayer_account', 'Principal', 'User', 'DataUtils','$filter','$state', 'Cfdi', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'Cfdi_states'];

    function Taxpayer_accountInboxController ($scope, Cfdi_type_doc, entity, Taxpayer_account, Principal, User, DataUtils, $filter, $state, Cfdi, ParseLinks, AlertService, pagingParams, paginationConstants, Cfdi_states) {
        var vm = this;
        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.searchSending = searchSending;
        vm.searchReciever = searchReciever;
        vm.searchCancel = searchCancel;
        vm.cancelar = cancelar;
        vm.campo = 'date_expedition';
        vm.orden = true;
        var today = new Date();
        vm.showmenu = 'OK';
        vm.user = {};
        vm.account = null;
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        //vm.loadAll();
        vm.folio_fiscal = null;
        vm.rfc_receiver = null;
        vm.dateStart = null;
        vm.dateEnd = null;
        vm.folio = null;
        vm.serie = null;
        vm.cfdi_type_doc = null;
        vm.cfdi_type_docs = Cfdi_type_doc.query({filtername:" "});
        vm.cfdi_all = null;


        function loadAll () {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')("0000-01-01", dateFormat);
            var toDate = $filter('date')("0000-01-01", dateFormat);
            Cfdi.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                folio_fiscal: " ",
                rfc_receiver: " ",
                fromDate: fromDate,
                toDate: toDate,
                idcfdi_type_doc: 0,
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
                vm.cfdis = data;
                vm.cfdi_all = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function searchReciever(){
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

            var serie = " ";
            if(vm.serie != null && vm.serie != ""){
                serie = vm.serie;
            }
            var rfcreceiver = " ";
            if(vm.rfc_receiver != null && vm.rfc_receiver != ""){
                rfcreceiver = vm.rfc_receiver;
            }
            var idcfdi_type_doc = 0;
            if(vm.cfdi_type_doc != null){
                idcfdi_type_doc = vm.cfdi_type_doc.id;
            }
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
                idaccount: vm.taxpayer_account.id,
                pre: 0,
                send: 0,
                cancel: 0,
                reciever: 1
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
                vm.cfdisRecieve = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function searchCancel(){
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

            var serie = " ";
            if(vm.serie != null && vm.serie != ""){
                serie = vm.serie;
            }
            var rfcreceiver = " ";
            if(vm.rfc_receiver != null && vm.rfc_receiver != ""){
                rfcreceiver = vm.rfc_receiver;
            }
            var idcfdi_type_doc = 0;
            if(vm.cfdi_type_doc != null){
                idcfdi_type_doc = vm.cfdi_type_doc.id;
            }
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
                idaccount: vm.taxpayer_account.id,
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
                vm.cfdisCancel = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function searchSending() {
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

            var serie = " ";
            if(vm.serie != null && vm.serie != ""){
                serie = vm.serie;
            }
            var rfcreceiver = " ";
            if(vm.rfc_receiver != null && vm.rfc_receiver != ""){
                rfcreceiver = vm.rfc_receiver;
            }
            var idcfdi_type_doc = 0;
            if(vm.cfdi_type_doc != null){
                idcfdi_type_doc = vm.cfdi_type_doc.id;
            }
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
                idaccount: vm.taxpayer_account.id,
                pre: 0,
                send: 1,
                cancel: 0,
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

        function cancelar(cfdi){
            cfdi.cfdi_states = vm.states[1];

            cfdi = Cfdi.update(cfdi);
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
