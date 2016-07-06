(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDialogController', Free_emitterDialogController);

    Free_emitterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'User'];

    function Free_emitterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, User) {
        var vm = this;
        vm.free_emitter = entity;
        vm.tax_regimes = Tax_regime.query();
        vm.c_countries = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_emitterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_emitter.id !== null) {
                Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
            } else {
                Free_emitter.save(vm.free_emitter, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.create_date = false;

        vm.setFilecertificate = function ($file, free_emitter) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        free_emitter.filecertificate = base64Data;
                        free_emitter.filecertificateContentType = $file.type;
                    });
                });
            }
        };

        vm.setFilekey = function ($file, free_emitter) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        free_emitter.filekey = base64Data;
                        free_emitter.filekeyContentType = $file.type;
                    });
                });
            }
        };

        vm.setFilelogo = function ($file, free_emitter) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        free_emitter.filelogo = base64Data;
                        free_emitter.filelogoContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
