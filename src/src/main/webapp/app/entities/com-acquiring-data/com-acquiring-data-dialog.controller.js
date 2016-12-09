(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_acquiring_dataDialogController', Com_acquiring_dataDialogController);

    Com_acquiring_dataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_acquiring_data', 'Com_public_notaries'];

    function Com_acquiring_dataDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Com_acquiring_data, Com_public_notaries) {
        var vm = this;
        vm.com_acquiring_data = entity;
        vm.com_public_notariess = Com_public_notaries.query({filter: 'com_acquiring_data-is-null'});
        $q.all([vm.com_acquiring_data.$promise, vm.com_public_notariess.$promise]).then(function() {
            if (!vm.com_acquiring_data.com_public_notaries || !vm.com_acquiring_data.com_public_notaries.id) {
                return $q.reject();
            }
            return Com_public_notaries.get({id : vm.com_acquiring_data.com_public_notaries.id}).$promise;
        }).then(function(com_public_notaries) {
            vm.com_public_notariess.push(com_public_notaries);
        });
        vm.load = function(id) {
            Com_acquiring_data.get({id : id}, function(result) {
                vm.com_acquiring_data = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_acquiring_dataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_acquiring_data.id !== null) {
                Com_acquiring_data.update(vm.com_acquiring_data, onSaveSuccess, onSaveError);
            } else {
                Com_acquiring_data.save(vm.com_acquiring_data, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
