(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('General_dataDialogController', General_dataDialogController);

    General_dataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'General_data'];

    function General_dataDialogController ($scope, $stateParams, $uibModalInstance, DataUtils, entity, General_data) {
        var vm = this;
        vm.general_data = entity;
        vm.load = function(id) {
            General_data.get({id : id}, function(result) {
                vm.general_data = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:general_dataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.general_data.id !== null) {
                General_data.update(vm.general_data, onSaveSuccess, onSaveError);
            } else {
                General_data.save(vm.general_data, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.setLogo = function ($file, general_data) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        general_data.logo = base64Data;
                        general_data.logoContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
