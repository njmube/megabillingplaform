(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_tarDialogController', C_tarDialogController);

    C_tarDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_tar'];

    function C_tarDialogController ($scope, $stateParams, $uibModalInstance, entity, C_tar) {
        var vm = this;
        vm.c_tar = entity;
        vm.load = function(id) {
            C_tar.get({id : id}, function(result) {
                vm.c_tar = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_tarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_tar.id !== null) {
                C_tar.update(vm.c_tar, onSaveSuccess, onSaveError);
            } else {
                C_tar.save(vm.c_tar, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
