(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_taxlegendsDialogController', Freecom_taxlegendsDialogController);

    Freecom_taxlegendsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_taxlegends', 'Free_cfdi'];

    function Freecom_taxlegendsDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_taxlegends, Free_cfdi) {
        var vm = this;
        vm.freecom_taxlegends = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_taxlegends-is-null'});
        $q.all([vm.freecom_taxlegends.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_taxlegends.free_cfdi || !vm.freecom_taxlegends.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_taxlegends.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_taxlegends.get({id : id}, function(result) {
                vm.freecom_taxlegends = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_taxlegendsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_taxlegends.id !== null) {
                Freecom_taxlegends.update(vm.freecom_taxlegends, onSaveSuccess, onSaveError);
            } else {
                Freecom_taxlegends.save(vm.freecom_taxlegends, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
