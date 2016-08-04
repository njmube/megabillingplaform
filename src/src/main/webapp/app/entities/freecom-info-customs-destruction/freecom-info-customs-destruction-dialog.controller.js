(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_info_customs_destructionDialogController', Freecom_info_customs_destructionDialogController);

    Freecom_info_customs_destructionDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_info_customs_destruction', 'Freecom_destruction_certificate'];

    function Freecom_info_customs_destructionDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_info_customs_destruction, Freecom_destruction_certificate) {
        var vm = this;
        vm.freecom_info_customs_destruction = entity;
        vm.freecom_destruction_certificates = Freecom_destruction_certificate.query({filter: 'freecom_info_customs_destruction-is-null'});
        $q.all([vm.freecom_info_customs_destruction.$promise, vm.freecom_destruction_certificates.$promise]).then(function() {
            if (!vm.freecom_info_customs_destruction.freecom_destruction_certificate || !vm.freecom_info_customs_destruction.freecom_destruction_certificate.id) {
                return $q.reject();
            }
            return Freecom_destruction_certificate.get({id : vm.freecom_info_customs_destruction.freecom_destruction_certificate.id}).$promise;
        }).then(function(freecom_destruction_certificate) {
            vm.freecom_destruction_certificates.push(freecom_destruction_certificate);
        });
        vm.load = function(id) {
            Freecom_info_customs_destruction.get({id : id}, function(result) {
                vm.freecom_info_customs_destruction = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_info_customs_destructionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_info_customs_destruction.id !== null) {
                Freecom_info_customs_destruction.update(vm.freecom_info_customs_destruction, onSaveSuccess, onSaveError);
            } else {
                Freecom_info_customs_destruction.save(vm.freecom_info_customs_destruction, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_expedition = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
