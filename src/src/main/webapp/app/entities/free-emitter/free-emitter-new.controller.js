(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterNewController', Free_emitterNewController);

    Free_emitterNewController.$inject = ['$scope', '$stateParams', '$q', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code', 'User', 'Free_digital_certificate'];

    function Free_emitterNewController ($scope, $stateParams, $q, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code, User, Free_digital_certificate) {
        var vm = this;
        vm.free_emitter = entity;
        vm.tax_regimes = Tax_regime.query();
        vm.c_countrys = C_country.query({pg:1});
        vm.c_states = null;
        vm.onChangeC_country = onChangeC_country;
        vm.c_municipalitys = null;
        vm.onChangeC_state = onChangeC_state;
        vm.c_locations = null;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.c_colonys = null;
        vm.onChangeC_location = onChangeC_location;
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
            });
        };

        function onChangeC_country () {
            var countryId = vm.free_emitter.c_country.id;
            C_state.query({countryId: countryId}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.free_emitter.c_state.id;
            C_municipality.query({stateId: stateId}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.free_emitter.c_municipality.id;
            C_location.query({municipalityId: municipalityId}, function(result){
                vm.c_locations = result;
            });
        }

        function onChangeC_location () {
            var locationId = vm.free_emitter.c_location.id;
            C_colony.query({locationId: locationId}, function(result){
                vm.c_colonys = result;
            });
        }

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_emitterUpdate', result);
            //$uibModalInstance.close(result);
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

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.create_date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
