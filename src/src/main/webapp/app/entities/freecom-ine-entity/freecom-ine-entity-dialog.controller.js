(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ine_entityDialogController', Freecom_ine_entityDialogController);

    Freecom_ine_entityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_ine_entity', 'Key_entity', 'Freecom_ine', 'C_scope_type'];

    function Freecom_ine_entityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_ine_entity, Key_entity, Freecom_ine, C_scope_type) {
        var vm = this;
        vm.freecom_ine_entity = entity;
        vm.key_entities = Key_entity.query();
        vm.freecom_ines = Freecom_ine.query();
        vm.c_scope_types = C_scope_type.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_ine_entityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_ine_entity.id !== null) {
                Freecom_ine_entity.update(vm.freecom_ine_entity, onSaveSuccess, onSaveError);
            } else {
                Freecom_ine_entity.save(vm.freecom_ine_entity, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
