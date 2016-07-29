(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_federal_entityDialogController', C_federal_entityDialogController);

    C_federal_entityDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_federal_entity'];

    function C_federal_entityDialogController ($scope, $stateParams, $uibModalInstance, entity, C_federal_entity) {
        var vm = this;
        vm.c_federal_entity = entity;
        vm.load = function(id) {
            C_federal_entity.get({id : id}, function(result) {
                vm.c_federal_entity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_federal_entityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_federal_entity.id !== null) {
                C_federal_entity.update(vm.c_federal_entity, onSaveSuccess, onSaveError);
            } else {
                C_federal_entity.save(vm.c_federal_entity, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
