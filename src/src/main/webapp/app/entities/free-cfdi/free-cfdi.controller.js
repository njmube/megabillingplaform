(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiController', Free_cfdiController);

    Free_cfdiController.$inject = ['$scope', '$uibModal', 'Principal', 'User', 'DataUtils','$filter','$state', 'Free_cfdi', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'Cfdi_states'];

    function Free_cfdiController ($scope, $uibModal, Principal, User, DataUtils, $filter, $state, Free_cfdi, ParseLinks, AlertService, pagingParams, paginationConstants, Cfdi_states) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.search = search;
        vm.cancelar = cancelar;
        vm.abrirZip = abrirZip;
        vm.campo = 'date_expedition';
        vm.orden = true;
        vm.hidemenu = hidemenu;
        var today = new Date();
        vm.showmenu = 'OK';
        vm.user = {};
        vm.account = null;
        vm.restDate = restDate;
        vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.messegeUser = messegeUser;
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
            vm.restDate();
            vm.messegeUser();
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
            vm.restDate();

            vm.messegeUser();
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

        function messegeUser(){
            Principal.identity().then(function(account) {
                if(account != null){
                    vm.isUser = account.authorities.indexOf('ROLE_USER') != -1;
                    vm.isNoAdmin = account.authorities.indexOf('ROLE_ADMIN') == -1;

                    if(vm.isUser && vm.isNoAdmin){
                        $uibModal.open({
                            templateUrl: 'app/home/messegeUser.html',
                            controller: 'MessegeUserController',
                            controllerAs: 'vm',
                            backdrop: true,
                            size: '',
                            resolve: {
                                entity: function () {
                                    return 0;
                                }
                            }
                        }).result.then(function () {

                            }, function () {
                            });
                    }
                }

            });
        }

        function restDate(){
            Principal.identity().then(function(account) {
                vm.account = account;
                if(vm.account != null){
                    User.get({login: vm.account.login}, function(result) {
                        vm.user = result;
                    });
                }
            });
        }

        function hidemenu(datecreated){
            var fechacreado = new Date(datecreated);
            var dias = (vm.toDate.getTime() - fechacreado.getTime())/86400000;
            var resto = 0;

            if(dias >= 15){
                return null;
            }
            if(dias < 15){
                return 'OK';
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
            free_cfdi = Free_cfdi.update(free_cfdi_dto);
            //loadAll();
        }

        function abrirZip(free_cfdi){
            var zipfile = Free_cfdi.getzip({idFree_cfdi:free_cfdi.id});

            var blob = new Blob([zipfile], {type: "application/zip"});
            var url = window.URL.createObjectURL(blob);
            document.location = url;
            /*var zipfile = temp.filexml;

            var json = JSON.stringify(zipfile),
                blob = new Blob([json], {type: "octet/stream"}),
                url = window.URL.createObjectURL(blob);
            document.location = url;*/

            /*var json = JSON.stringify(zipfile);
            var file = new Blob([json], { type: 'application/pdf' });
            document.location = window.URL.createObjectURL(file);*/
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            /*$state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });*/
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
                page: vm.page - 1,
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
                //vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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
