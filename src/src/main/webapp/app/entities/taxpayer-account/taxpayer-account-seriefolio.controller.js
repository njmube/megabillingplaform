(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountSerieFolioController', Taxpayer_accountSerieFolioController);

    Taxpayer_accountSerieFolioController.$inject = ['$rootScope','$uibModal','Taxpayer_account','entity', '$scope', '$state', 'Taxpayer_series_folio', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function Taxpayer_accountSerieFolioController ($rootScope, $uibModal, Taxpayer_account, entity, $scope, $state, Taxpayer_series_folio, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.add = add;
        vm.campo = 'date_creation';
        vm.orden = true;
        vm.deleteSerieFolio = deleteSerieFolio;
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;
        vm.editSerieFolio = editSerieFolio;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);

        loadAll();

        function loadAll () {
            Taxpayer_series_folio.query({
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
                vm.taxpayer_series_folios = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function changeAccount(){
            window.location.assign(getAbsolutePath()+vm.taxpayer_account.id);
        }

        function getAbsolutePath() {
            var loc = window.location.href;
            var pathName = loc.substring(0, loc.lastIndexOf('/') + 1);
            return pathName;
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

        function add(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-account/add-seriefolio.html',
                controller: 'AddSerieFolioController',
                controllerAs: 'vm',
                backdrop: true,
                size: '',
                resolve: {
                    entity: function () {
                        return vm.taxpayer_account;
                    }
                }
            }).result.then(function(result) {
                    vm.taxpayer_account = result.taxpayer_account;
                    loadAll();
                }, function() {

                });

        }

        function deleteSerieFolio(taxpayerSerieFolio){
            Taxpayer_series_folio.delete({id: taxpayerSerieFolio.id},
                function () {
                    loadAll();
                });
        }

        function editSerieFolio(taxpayerSerieFolio){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-account/dialog-seriefolio.html',
                controller: 'DialogSerieFolioController',
                controllerAs: 'vm',
                backdrop: true,
                size: '',
                resolve: {
                    entity: function () {
                        return taxpayerSerieFolio;
                    }
                }
            }).result.then(function() {
                    loadAll();
                }, function() {
                    loadAll();
                });
        }
    }
})();


