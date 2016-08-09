(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ine_entityDetailController', Freecom_ine_entityDetailController);

    Freecom_ine_entityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_ine_entity', 'Key_entity', 'Freecom_ine', 'C_scope_type'];

    function Freecom_ine_entityDetailController($scope, $rootScope, $stateParams, entity, Freecom_ine_entity, Key_entity, Freecom_ine, C_scope_type) {
        var vm = this;
        vm.freecom_ine_entity = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_ine_entityUpdate', function(event, result) {
            vm.freecom_ine_entity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
