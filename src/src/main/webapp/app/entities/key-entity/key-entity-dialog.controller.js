(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Key_entityDialogController', Key_entityDialogController);

    Key_entityDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Key_entity'];

    function Key_entityDialogController ($scope, $stateParams, $uibModalInstance, entity, Key_entity) {
        var vm = this;
        vm.key_entity = entity;
        vm.load = function(id) {
            Key_entity.get({id : id}, function(result) {
                vm.key_entity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:key_entityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.key_entity.id !== null) {
                Key_entity.update(vm.key_entity, onSaveSuccess, onSaveError);
            } else {
                Key_entity.save(vm.key_entity, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
