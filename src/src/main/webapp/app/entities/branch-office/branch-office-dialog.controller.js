(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Branch_officeDialogController', Branch_officeDialogController);

    Branch_officeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Branch_office', 'Tax_address', 'Taxpayer_account'];

    function Branch_officeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Branch_office, Tax_address, Taxpayer_account) {
        var vm = this;

        vm.branch_office = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tax_addresses = Tax_address.query();
        vm.taxpayer_accounts = Taxpayer_account.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.branch_office.id !== null) {
                Branch_office.update(vm.branch_office, onSaveSuccess, onSaveError);
            } else {
                Branch_office.save(vm.branch_office, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:branch_officeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
