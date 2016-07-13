(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_pficDialogController', Freecom_pficDialogController);

    Freecom_pficDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_pfic', 'Free_cfdi'];

    function Freecom_pficDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_pfic, Free_cfdi) {
        var vm = this;
        vm.freecom_pfic = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_pfic-is-null'});
        $q.all([vm.freecom_pfic.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_pfic.free_cfdi || !vm.freecom_pfic.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_pfic.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.load = function(id) {
            Freecom_pfic.get({id : id}, function(result) {
                vm.freecom_pfic = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_pficUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_pfic.id !== null) {
                Freecom_pfic.update(vm.freecom_pfic, onSaveSuccess, onSaveError);
            } else {
                Freecom_pfic.save(vm.freecom_pfic, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
