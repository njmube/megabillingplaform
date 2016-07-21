(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_committee_typeDialogController', C_committee_typeDialogController);

    C_committee_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_committee_type'];

    function C_committee_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, C_committee_type) {
        var vm = this;
        vm.c_committee_type = entity;
        vm.load = function(id) {
            C_committee_type.get({id : id}, function(result) {
                vm.c_committee_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_committee_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_committee_type.id !== null) {
                C_committee_type.update(vm.c_committee_type, onSaveSuccess, onSaveError);
            } else {
                C_committee_type.save(vm.c_committee_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
