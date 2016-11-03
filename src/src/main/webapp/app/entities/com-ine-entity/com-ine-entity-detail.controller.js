(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ine_entityDetailController', Com_ine_entityDetailController);

    Com_ine_entityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_ine_entity', 'Key_entity', 'Com_ine', 'C_scope_type'];

    function Com_ine_entityDetailController($scope, $rootScope, $stateParams, entity, Com_ine_entity, Key_entity, Com_ine, C_scope_type) {
        var vm = this;
        vm.com_ine_entity = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_ine_entityUpdate', function(event, result) {
            vm.com_ine_entity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
