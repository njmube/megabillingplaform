(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_roadDialogController', C_type_roadDialogController);

    C_type_roadDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_type_road'];

    function C_type_roadDialogController ($scope, $stateParams, $uibModalInstance, entity, C_type_road) {
        var vm = this;
        vm.c_type_road = entity;
        vm.load = function(id) {
            C_type_road.get({id : id}, function(result) {
                vm.c_type_road = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_type_roadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_type_road.id !== null) {
                C_type_road.update(vm.c_type_road, onSaveSuccess, onSaveError);
            } else {
                C_type_road.save(vm.c_type_road, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
