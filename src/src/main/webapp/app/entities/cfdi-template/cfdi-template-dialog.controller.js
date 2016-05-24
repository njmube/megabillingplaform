(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_templateDialogController', Cfdi_templateDialogController);

    Cfdi_templateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Cfdi_template'];

    function Cfdi_templateDialogController ($scope, $stateParams, $uibModalInstance, DataUtils, entity, Cfdi_template) {
        var vm = this;
        vm.cfdi_template = entity;
        vm.load = function(id) {
            Cfdi_template.get({id : id}, function(result) {
                vm.cfdi_template = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:cfdi_templateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cfdi_template.id !== null) {
                Cfdi_template.update(vm.cfdi_template, onSaveSuccess, onSaveError);
            } else {
                Cfdi_template.save(vm.cfdi_template, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.setFile = function ($file, cfdi_template) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        cfdi_template.file = base64Data;
                        cfdi_template.fileContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
