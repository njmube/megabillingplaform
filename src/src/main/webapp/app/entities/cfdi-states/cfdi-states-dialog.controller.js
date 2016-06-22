(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_statesDialogController', Cfdi_statesDialogController);

    Cfdi_statesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cfdi_states'];

    function Cfdi_statesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cfdi_states) {
        var vm = this;

        vm.cfdi_states = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cfdi_states.id !== null) {
                Cfdi_states.update(vm.cfdi_states, onSaveSuccess, onSaveError);
            } else {
                Cfdi_states.save(vm.cfdi_states, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:cfdi_statesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
