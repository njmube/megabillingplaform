(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_scope_typeDialogController', C_scope_typeDialogController);

    C_scope_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_scope_type'];

    function C_scope_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, C_scope_type) {
        var vm = this;
        vm.c_scope_type = entity;
        vm.load = function(id) {
            C_scope_type.get({id : id}, function(result) {
                vm.c_scope_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_scope_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_scope_type.id !== null) {
                C_scope_type.update(vm.c_scope_type, onSaveSuccess, onSaveError);
            } else {
                C_scope_type.save(vm.c_scope_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
