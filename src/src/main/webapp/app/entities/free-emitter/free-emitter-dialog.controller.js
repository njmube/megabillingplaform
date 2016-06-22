(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDialogController', Free_emitterDialogController);

    Free_emitterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code'];

    function Free_emitterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code) {
        var vm = this;

        vm.free_emitter = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tax_regimes = Tax_regime.query();
        vm.c_countries = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_locations = C_location.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.free_emitter.id !== null) {
                Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
            } else {
                Free_emitter.save(vm.free_emitter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:free_emitterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPath_certificate = function ($file, free_emitter) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        free_emitter.path_certificate = base64Data;
                        free_emitter.path_certificateContentType = $file.type;
                    });
                });
            }
        };

        vm.setPrivate_key = function ($file, free_emitter) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        free_emitter.private_key = base64Data;
                        free_emitter.private_keyContentType = $file.type;
                    });
                });
            }
        };

    }
})();
