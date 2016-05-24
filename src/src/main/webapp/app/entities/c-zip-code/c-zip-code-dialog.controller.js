(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_zip_codeDialogController', C_zip_codeDialogController);

    C_zip_codeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_zip_code', 'C_location'];

    function C_zip_codeDialogController ($scope, $stateParams, $uibModalInstance, entity, C_zip_code, C_location) {
        var vm = this;
        vm.c_zip_code = entity;
        vm.c_locations = C_location.query();
        vm.load = function(id) {
            C_zip_code.get({id : id}, function(result) {
                vm.c_zip_code = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_zip_codeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_zip_code.id !== null) {
                C_zip_code.update(vm.c_zip_code, onSaveSuccess, onSaveError);
            } else {
                C_zip_code.save(vm.c_zip_code, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
