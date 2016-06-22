(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDetailController', Free_emitterDetailController);

    Free_emitterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code'];

    function Free_emitterDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code) {
        var vm = this;

        vm.free_emitter = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_emitterUpdate', function(event, result) {
            vm.free_emitter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
