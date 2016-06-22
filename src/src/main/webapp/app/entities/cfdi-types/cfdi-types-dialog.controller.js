(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_typesDialogController', Cfdi_typesDialogController);

    Cfdi_typesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cfdi_types'];

    function Cfdi_typesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cfdi_types) {
        var vm = this;

        vm.cfdi_types = entity;
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
            if (vm.cfdi_types.id !== null) {
                Cfdi_types.update(vm.cfdi_types, onSaveSuccess, onSaveError);
            } else {
                Cfdi_types.save(vm.cfdi_types, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:cfdi_typesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
