(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_retentionsDialogController', Freecom_local_retentionsDialogController);

    Freecom_local_retentionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_local_retentions', 'Freecom_local_taxes'];

    function Freecom_local_retentionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_local_retentions, Freecom_local_taxes) {
        var vm = this;

        vm.freecom_local_retentions = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_local_taxes = Freecom_local_taxes.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_local_retentions.id !== null) {
                Freecom_local_retentions.update(vm.freecom_local_retentions, onSaveSuccess, onSaveError);
            } else {
                Freecom_local_retentions.save(vm.freecom_local_retentions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_local_retentionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
