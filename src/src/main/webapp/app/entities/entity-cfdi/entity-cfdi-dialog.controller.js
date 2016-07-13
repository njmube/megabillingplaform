(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Entity_cfdiDialogController', Entity_cfdiDialogController);

    Entity_cfdiDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entity_cfdi', 'Accounting', 'Key_entity', 'Scope', 'Freecom_ine'];

    function Entity_cfdiDialogController ($scope, $stateParams, $uibModalInstance, entity, Entity_cfdi, Accounting, Key_entity, Scope, Freecom_ine) {
        var vm = this;
        vm.entity_cfdi = entity;
        vm.accountings = Accounting.query();
        vm.key_entitys = Key_entity.query();
        vm.scopes = Scope.query();
        vm.freecom_ines = Freecom_ine.query();
        vm.load = function(id) {
            Entity_cfdi.get({id : id}, function(result) {
                vm.entity_cfdi = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:entity_cfdiUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.entity_cfdi.id !== null) {
                Entity_cfdi.update(vm.entity_cfdi, onSaveSuccess, onSaveError);
            } else {
                Entity_cfdi.save(vm.entity_cfdi, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
