(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ComplementDialogController', ComplementDialogController);

    ComplementDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Complement'];

    function ComplementDialogController ($scope, $stateParams, $uibModalInstance, entity, Complement) {
        var vm = this;
        vm.complement = entity;
        vm.load = function(id) {
            Complement.get({id : id}, function(result) {
                vm.complement = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:complementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.complement.id !== null) {
                Complement.update(vm.complement, onSaveSuccess, onSaveError);
            } else {
                Complement.save(vm.complement, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
