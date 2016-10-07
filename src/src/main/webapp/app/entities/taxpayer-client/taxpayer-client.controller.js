(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientController', Taxpayer_clientController);

    Taxpayer_clientController.$inject = ['$scope', '$state', '$uibModal', 'Taxpayer_client', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'taxpayer_account_entity', 'Taxpayer_account'];

    function Taxpayer_clientController ($scope, $state, $uibModal, Taxpayer_client, ParseLinks, AlertService, pagingParams, paginationConstants, taxpayer_account_entity, Taxpayer_account) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        vm.taxpayer_account = taxpayer_account_entity;

        vm.taxpayer_accounts = Taxpayer_account.query({
            page: 0,
            size: 10
        });

        vm.onChangeTaxPayerAccount = function(){
            if(vm.taxpayer_account == null) {
                vm.taxpayer_account = {id: 0};
                loadAll();
                vm.taxpayer_account = null;
            }
            else {
                loadAll();
            }
        };

        loadAll();

        function loadAll () {
            Taxpayer_client.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort(),
                taxpayeraccount: vm.taxpayer_account.id
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
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        vm.addTaxpayerClient = addTaxpayerClient;
        vm.viewTaxpayerClient = viewTaxpayerClient;
        vm.editTaxpayerClient = editTaxpayerClient;
        vm.deleteTaxpayerClient = deleteTaxpayerClient;

        function addTaxpayerClient(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-client/taxpayer-client-dialog.html',
                controller: 'Taxpayer_clientDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            rfc: null,
                            bussinesname: null,
                            email: null,
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

        function viewTaxpayerClient(id){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-client/taxpayer-client-detail.html',
                controller: 'Taxpayer_clientDetailController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return Taxpayer_client.get({id: id}).$promise;
                    }
                }
            });
        }

        function editTaxpayerClient(id){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-client/taxpayer-client-dialog.html',
                controller: 'Taxpayer_clientDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
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
