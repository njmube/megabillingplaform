(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterDetailController', Free_emitterDetailController);

    Free_emitterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'User'];

    function Free_emitterDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, User) {
        var vm = this;
        vm.free_emitter = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_emitterUpdate', function(event, result) {
            vm.free_emitter = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
