(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDialogController', Free_emitterDialogController);

    Free_emitterDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code', 'User', 'Free_digital_certificate'];

    function Free_emitterDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code, User, Free_digital_certificate) {
        var vm = this;
        vm.free_emitter = entity;
        /*if (vm.free_emitter.id == null) {
            vm.free_emitter.create_date = Date().getNow();
        }*/
        vm.tax_regimes = Tax_regime.query();
        vm.c_countrys = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalitys = C_municipality.query();
        vm.c_locations = C_location.query();
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.users = User.query();
        vm.free_digital_certificates = Free_digital_certificate.query({filter: 'free_emitter-is-null'});
        $q.all([vm.free_emitter.$promise, vm.free_digital_certificates.$promise]).then(function() {
            if (!vm.free_emitter.free_digital_certificate || !vm.free_emitter.free_digital_certificate.id) {
                return $q.reject();
            }
            return Free_digital_certificate.get({id : vm.free_emitter.free_digital_certificate.id}).$promise;
        }).then(function(free_digital_certificate) {
            vm.free_digital_certificates.push(free_digital_certificate);
        });
        vm.load = function(id) {
            Free_emitter.get({id : id}, function(result) {
                vm.free_emitter = result;
                if (vm.free_emitter.id == null) {
                    vm.free_emitter.create_date = Date.now();
                }
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
    }
})();
