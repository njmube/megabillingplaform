(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDialogController', Free_emitterDialogController);

    Free_emitterDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code', 'User'];

    function Free_emitterDialogController ($scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code, User) {
        var vm = this;
        vm.free_emitter = entity;
        vm.tax_regimes = Tax_regime.query();
        vm.c_countrys = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalitys = C_municipality.query();
        vm.c_locations = C_location.query();
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.users = User.query();
        vm.load = function(id) {
            Free_emitter.get({id : id}, function(result) {
                vm.free_emitter = result;
            });
        };

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

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();
