(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('General_dataNewController', General_dataNewController);

    General_dataNewController.$inject = ['$scope', '$stateParams', 'DataUtils', 'entity', 'General_data'];

    function General_dataNewController ($scope, $stateParams, DataUtils, entity, General_data) {
        var vm = this;
        vm.general_data = entity;
        vm.load = function() {
            General_data.get({id : 0}, function(result) {
                vm.general_data = result;
            });
        };

        var onSaveSuccess = function (result) {
            vm.general_data = result;
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

