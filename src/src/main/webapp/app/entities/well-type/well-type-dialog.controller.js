(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Well_typeDialogController', Well_typeDialogController);

    Well_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Well_type'];

    function Well_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, Well_type) {
        var vm = this;
        vm.well_type = entity;
        vm.load = function(id) {
            Well_type.get({id : id}, function(result) {
                vm.well_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:well_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.well_type.id !== null) {
                Well_type.update(vm.well_type, onSaveSuccess, onSaveError);
            } else {
                Well_type.save(vm.well_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
