(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientController', Taxpayer_clientController);

    Taxpayer_clientController.$inject = ['$scope', '$state', '$uibModal', 'Taxpayer_client', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'taxpayer_account_entity', 'Taxpayer_account'];

    function Taxpayer_clientController ($scope, $state, $uibModal, Taxpayer_client, ParseLinks, AlertService, pagingParams, paginationConstants, taxpayer_account_entity, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_accounts = Taxpayer_account.query({
            page: 0,
            size: 10
        });

        vm.onChangeTaxPayerAccount = onChangeTaxPayerAccount;

        function onChangeTaxPayerAccount(){
            if(vm.taxpayer_account == null) {
                vm.taxpayer_account = {id: 0};
                loadAll();
                vm.taxpayer_account = null;
            }
            else {
                loadAll();
            }
        }

        vm.rfc = null;
        vm.bussinesname = null;
        vm.email = null;
        vm.phone = null;

        vm.search = search;

        function search(){
            loadAll();
        }

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        loadAll();

        function loadAll () {
            var rfc = " ";
            var bussinesname = " ";
            var email = " ";
            var phone = " ";

            if(vm.rfc != null && vm.rfc != "") {
                rfc = vm.rfc;
            }

            if(vm.bussinesname != null && vm.bussinesname != "") {
                bussinesname = vm.bussinesname;
            }

            if(vm.email != null && vm.email != "") {
                email = vm.email;
            }

            if(vm.phone != null && vm.phone != "") {
                phone = vm.phone;
            }

            Taxpayer_client.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                taxpayeraccount: vm.taxpayer_account.id,
                rfc: rfc,
                bussinesname: bussinesname,
                email: email,
                phone: phone
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
                vm.taxpayer_clients = data;
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
                id: vm.taxpayer_account.id,
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        vm.addTaxpayerClient = addTaxpayerClient;
        vm.editTaxpayerClient = editTaxpayerClient;
        vm.deleteTaxpayerClient = deleteTaxpayerClient;

        function addTaxpayerClient(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-client/taxpayer-client-dialog.html',
                controller: 'Taxpayer_clientDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            rfc: null,
                            bussinesname: null,
                            email: null,
                            email2: null,
                            phone: null,
                            id: null
                        };
                    },
                    taxpayer_account_entity: function() {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }

        function editTaxpayerClient(id){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-client/taxpayer-client-dialog.html',
                controller: 'Taxpayer_clientDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function() {
                        return Taxpayer_client.get({id : id}).$promise;
                    },
                    taxpayer_account_entity: function() {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }

        function deleteTaxpayerClient(id){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-client/taxpayer-client-delete-dialog.html',
                controller: 'Taxpayer_clientDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: function() {
                        return Taxpayer_client.get({id : id}).$promise;
                    }
                }
            }).result.then(function() {
                loadAll();
            });
        }
    }
})();
