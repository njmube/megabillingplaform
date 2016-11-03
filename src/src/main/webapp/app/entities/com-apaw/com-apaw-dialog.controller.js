(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_apawDialogController', Com_apawDialogController);

    Com_apawDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Com_apaw', 'Cfdi', 'C_well_type', 'C_acquired_title', 'C_features_work_piece'];

    function Com_apawDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Com_apaw, Cfdi, C_well_type, C_acquired_title, C_features_work_piece) {
        var vm = this;
        vm.com_apaw = entity;
        vm.cfdis = Cfdi.query({filter: 'com_apaw-is-null'});
        $q.all([vm.com_apaw.$promise, vm.cfdis.$promise]).then(function() {
            if (!vm.com_apaw.cfdi || !vm.com_apaw.cfdi.id) {
                return $q.reject();
            }
            return Cfdi.get({id : vm.com_apaw.cfdi.id}).$promise;
        }).then(function(cfdi) {
            vm.cfdis.push(cfdi);
        });
        vm.c_well_types = C_well_type.query();
        vm.c_acquired_titles = C_acquired_title.query();
        vm.c_features_work_pieces = C_features_work_piece.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_apawUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_apaw.id !== null) {
                Com_apaw.update(vm.com_apaw, onSaveSuccess, onSaveError);
            } else {
                Com_apaw.save(vm.com_apaw, onSaveSuccess, onSaveError);
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
