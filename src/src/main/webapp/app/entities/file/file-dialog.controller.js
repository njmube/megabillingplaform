(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('FileDialogController', FileDialogController);

    FileDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'File', 'File_state', 'File_type'];

    function FileDialogController ($scope, $stateParams, $uibModalInstance, entity, File, File_state, File_type) {
        var vm = this;
        vm.file = entity;
        vm.file_states = File_state.query();
        vm.file_types = File_type.query();
        vm.load = function(id) {
            File.get({id : id}, function(result) {
                vm.file = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:fileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.file.id !== null) {
                File.update(vm.file, onSaveSuccess, onSaveError);
            } else {
                File.save(vm.file, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
