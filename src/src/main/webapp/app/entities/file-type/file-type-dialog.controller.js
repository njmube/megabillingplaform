(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('File_typeDialogController', File_typeDialogController);

    File_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'File_type'];

    function File_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, File_type) {
        var vm = this;
        vm.file_type = entity;
        vm.load = function(id) {
            File_type.get({id : id}, function(result) {
                vm.file_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:file_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.file_type.id !== null) {
                File_type.update(vm.file_type, onSaveSuccess, onSaveError);
            } else {
                File_type.save(vm.file_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
