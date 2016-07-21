(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_apawDialogController', Freecom_apawDialogController);

    Freecom_apawDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_apaw', 'Free_cfdi', 'C_well_type', 'C_acquired_title', 'C_features_work_piece'];

    function Freecom_apawDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_apaw, Free_cfdi, C_well_type, C_acquired_title, C_features_work_piece) {
        var vm = this;
        vm.freecom_apaw = entity;
        vm.free_cfdis = Free_cfdi.query({filter: 'freecom_apaw-is-null'});
        $q.all([vm.freecom_apaw.$promise, vm.free_cfdis.$promise]).then(function() {
            if (!vm.freecom_apaw.free_cfdi || !vm.freecom_apaw.free_cfdi.id) {
                return $q.reject();
            }
            return Free_cfdi.get({id : vm.freecom_apaw.free_cfdi.id}).$promise;
        }).then(function(free_cfdi) {
            vm.free_cfdis.push(free_cfdi);
        });
        vm.c_well_types = C_well_type.query();
        vm.c_acquired_titles = C_acquired_title.query();
        vm.c_features_work_pieces = C_features_work_piece.query();
        vm.load = function(id) {
            Freecom_apaw.get({id : id}, function(result) {
                vm.freecom_apaw = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_apawUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_apaw.id !== null) {
                Freecom_apaw.update(vm.freecom_apaw, onSaveSuccess, onSaveError);
            } else {
                Freecom_apaw.save(vm.freecom_apaw, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date_acquisition = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
