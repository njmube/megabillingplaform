(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ServicesDialogController', ServicesDialogController);

    ServicesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Services'];

    function ServicesDialogController ($scope, $stateParams, $uibModalInstance, entity, Services) {
        var vm = this;
        vm.services = entity;
        vm.load = function(id) {
            Services.get({id : id}, function(result) {
                vm.services = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:servicesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.services.id !== null) {
                Services.update(vm.services, onSaveSuccess, onSaveError);
            } else {
                Services.save(vm.services, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
