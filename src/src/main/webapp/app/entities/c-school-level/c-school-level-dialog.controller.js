(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_school_levelDialogController', C_school_levelDialogController);

    C_school_levelDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_school_level'];

    function C_school_levelDialogController ($scope, $stateParams, $uibModalInstance, entity, C_school_level) {
        var vm = this;
        vm.c_school_level = entity;
        vm.load = function(id) {
            C_school_level.get({id : id}, function(result) {
                vm.c_school_level = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_school_levelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_school_level.id !== null) {
                C_school_level.update(vm.c_school_level, onSaveSuccess, onSaveError);
            } else {
                C_school_level.save(vm.c_school_level, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
