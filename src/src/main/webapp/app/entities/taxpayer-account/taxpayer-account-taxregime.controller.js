(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountTaxregimeController', Taxpayer_accountTaxregimeController);

    Taxpayer_accountTaxregimeController.$inject = ['$state', '$scope', '$uibModal', '$rootScope', '$stateParams', 'entity', 'Taxpayer_account', 'Tax_address', 'Taxpayer_certificate', 'Type_taxpayer', 'Tax_regime', 'User'];

    function Taxpayer_accountTaxregimeController($state, $scope, $uibModal, $rootScope, $stateParams, entity, Taxpayer_account, Tax_address, Taxpayer_certificate, Type_taxpayer, Tax_regime, User) {
        var vm = this;

        vm.page = 1;

        vm.transition = transition;
        vm.load = load;
        vm.taxpayer_account = entity;
        vm.taxpayer_accounts = Taxpayer_account.query();
        vm.add = add;
        vm.deleteTaxregime = deleteTaxregime;
        vm.getAbsolutePath = getAbsolutePath;
        vm.changeAccount = changeAccount;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_accountUpdate', function(event, result) {
            vm.taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);

        load();

        function load(){
            vm.queryCount = vm.taxpayer_account.tax_regimes.length;
            vm.totalItems = vm.queryCount;
        }

        function deleteTaxregime(taxregime){
            var list = [];
            var cont = 0;
            for(var i = 0; i< vm.taxpayer_account.tax_regimes.length; i++){
                if(taxregime.id != vm.taxpayer_account.tax_regimes[i].id){
                    list[cont] = vm.taxpayer_account.tax_regimes[i];
                    cont++;
                }
            }
            vm.taxpayer_account.tax_regimes = list;
            Taxpayer_account.update(vm.taxpayer_account, onSaveSuccess, onSaveError);
        }

        var onSaveSuccess = function (result) {
            vm.taxpayer_account =  result;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        function changeAccount(){
            window.location.assign(getAbsolutePath()+vm.taxpayer_account.id);
        }

        function getAbsolutePath() {
            var loc = window.location.href;
            var pathName = loc.substring(0, loc.lastIndexOf('/') + 1);
            return pathName;
        }

        function add(){
            $uibModal.open({
                templateUrl: 'app/entities/taxpayer-account/add-taxregime.html',
                controller: 'AddTaxregimeController',
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
                    load();
                }, function() {
                });
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

