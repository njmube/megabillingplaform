(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Rate_typeDialogController', Rate_typeDialogController);

    Rate_typeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rate_type'];

    function Rate_typeDialogController ($scope, $stateParams, $uibModalInstance, entity, Rate_type) {
        var vm = this;
        vm.rate_type = entity;
        vm.load = function(id) {
            Rate_type.get({id : id}, function(result) {
                vm.rate_type = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:rate_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.rate_type.id !== null) {
                Rate_type.update(vm.rate_type, onSaveSuccess, onSaveError);
            } else {
                Rate_type.save(vm.rate_type, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
