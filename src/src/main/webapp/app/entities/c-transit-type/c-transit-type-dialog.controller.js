(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_transit_typeDialogController', C_transit_typeDialogController);

    C_transit_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_transit_type'];

    function C_transit_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, C_transit_type) {
        var vm = this;
        vm.c_transit_type = entity;
        vm.load = function(id) {
            C_transit_type.get({id : id}, function(result) {
                vm.c_transit_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_transit_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_transit_type.id !== null) {
                C_transit_type.update(vm.c_transit_type, onSaveSuccess, onSaveError);
            } else {
                C_transit_type.save(vm.c_transit_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
