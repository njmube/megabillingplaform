(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_public_notariesDialogController', Freecom_public_notariesDialogController);

    Freecom_public_notariesDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_public_notaries', 'Free_cfdi'];

    function Freecom_public_notariesDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_public_notaries, Free_cfdi) {
        var vm = this;
        vm.freecom_public_notaries = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_public_notaries-is-null'});
        $q.all([vm.freecom_public_notaries.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_public_notaries.free_cfdi || !vm.freecom_public_notaries.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_public_notaries.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_public_notaries.get({id : id}, function(result) {
                vm.freecom_public_notaries = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_public_notariesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_public_notaries.id !== null) {
                Freecom_public_notaries.update(vm.freecom_public_notaries, onSaveSuccess, onSaveError);
            } else {
                Freecom_public_notaries.save(vm.freecom_public_notaries, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
