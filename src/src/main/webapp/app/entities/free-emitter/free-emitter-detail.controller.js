(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDetailController', Free_emitterDetailController);

    Free_emitterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code', 'User', 'Free_digital_certificate'];

    function Free_emitterDetailController($scope, $rootScope, $stateParams, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code, User, Free_digital_certificate) {
        var vm = this;
        vm.free_emitter = entity;
        vm.load = function (id) {
            Free_emitter.get({id: id}, function(result) {
                vm.free_emitter = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_emitterUpdate', function(event, result) {
            vm.free_emitter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
