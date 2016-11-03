(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_info_customs_destructionDialogController', Com_info_customs_destructionDialogController);

    Com_info_customs_destructionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_info_customs_destruction', 'Com_destruction_certificate'];

    function Com_info_customs_destructionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_info_customs_destruction, Com_destruction_certificate) {
        var vm = this;
        vm.com_info_customs_destruction = entity;
        vm.com_destruction_certificates = Com_destruction_certificate.query({filter: 'com_info_customs_destruction-is-null'});
        $q.all([vm.com_info_customs_destruction.$promise, vm.com_destruction_certificates.$promise]).then(function() {
            if (!vm.com_info_customs_destruction.com_destruction_certificate || !vm.com_info_customs_destruction.com_destruction_certificate.id) {
                return $q.reject();
            }
            return Com_destruction_certificate.get({id : vm.com_info_customs_destruction.com_destruction_certificate.id}).$promise;
        }).then(function(com_destruction_certificate) {
            vm.com_destruction_certificates.push(com_destruction_certificate);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_info_customs_destructionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_info_customs_destruction.id !== null) {
                Com_info_customs_destruction.update(vm.com_info_customs_destruction, onSaveSuccess, onSaveError);
            } else {
                Com_info_customs_destruction.save(vm.com_info_customs_destruction, onSaveSuccess, onSaveError);
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
