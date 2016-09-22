(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AddTaxregimeController', AddTaxregimeController);

    AddTaxregimeController.$inject = ['$scope', '$filter', '$stateParams', 'AlertService', '$uibModal', '$state', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime'];

    function AddTaxregimeController ($scope, $filter, $stateParams, AlertService, $uibModal, $state, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.showInfo = false;
        vm.edit = null;
        vm.tax_regimes = Tax_regime.query({filtername:" "});
        vm.add = add;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };


        function add(taxregime){
            vm.taxpayer_account.tax_regimes[vm.taxpayer_account.tax_regimes.length] = taxregime;
            Taxpayer_account.update(vm.taxpayer_account, onSaveSuccess, onSaveError);
        }

        var onSaveSuccess = function (result) {
            vm.taxpayer_account =  result;
            vm.showInfo = false;
            $uibModalInstance.close({
                taxpayer_account: vm.taxpayer_account
            });
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    }
})();



